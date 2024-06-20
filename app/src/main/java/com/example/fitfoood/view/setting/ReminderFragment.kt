import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentReminderBinding
import com.example.fitfoood.view.setting.AlarmReceiver
import com.example.fitfoood.view.setting.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReminderFragment : Fragment(), View.OnClickListener, TimePickerFragment.DialogTimeListener {

    private var binding: FragmentReminderBinding? = null
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var sharedPreferences: SharedPreferences

    private var isNotificationPermissionGranted = false

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                if (!isNotificationPermissionGranted) {
                    isNotificationPermissionGranted = true
                    Toast.makeText(requireContext(), "Notifications permission granted", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if notification permission is granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            isNotificationPermissionGranted = true
        }

        // Request notification permission if not granted
        if (!isNotificationPermissionGranted) {
            requestNotificationPermission()
        }

        sharedPreferences = requireContext().getSharedPreferences("AlarmPreferences", Context.MODE_PRIVATE)

        // Listener one time alarm
        binding?.setBreakfastTime?.setOnClickListener(this)
        binding?.setLunchTime?.setOnClickListener(this)
        binding?.setDinnerTime?.setOnClickListener(this)
        binding?.switchEatReminder?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    requestExactAlarmPermission()
                } else {
                    setAlarms()
                }
            } else {
                cancelAlarms()
            }
            saveAlarmState(isChecked)
        }

        alarmReceiver = AlarmReceiver()

        val tbTitle = view.findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.reminder)

        binding?.toolbar?.setOnClickListener {
            activity?.onBackPressed()
        }

        loadAlarmState()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            } else {
                setAlarms()
            }
        } else {
            setAlarms()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.set_breakfast_time -> {
                binding?.switchEatReminder?.isChecked = false
                val timePickerFragmentOne = TimePickerFragment()
                timePickerFragmentOne.setListener(this)
                timePickerFragmentOne.show(parentFragmentManager, BREAKFAST_TIME_PICKER_ONCE_TAG)
            }
            R.id.set_lunch_time -> {
                binding?.switchEatReminder?.isChecked = false
                val timePickerFragmentOne = TimePickerFragment()
                timePickerFragmentOne.setListener(this)
                timePickerFragmentOne.show(parentFragmentManager, LUNCH_TIME_PICKER_ONCE_TAG)
            }
            R.id.set_dinner_time -> {
                binding?.switchEatReminder?.isChecked = false
                val timePickerFragmentOne = TimePickerFragment()
                timePickerFragmentOne.setListener(this)
                timePickerFragmentOne.show(parentFragmentManager, DINNER_TIME_PICKER_ONCE_TAG)
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            BREAKFAST_TIME_PICKER_ONCE_TAG -> {
                binding?.tvBreakfastTime?.text = dateFormat.format(calendar.time)
                saveAlarmTime(BREAKFAST_ALARM_ID, dateFormat.format(calendar.time))
            }
            LUNCH_TIME_PICKER_ONCE_TAG -> {
                binding?.tvLunchTime?.text = dateFormat.format(calendar.time)
                saveAlarmTime(LUNCH_ALARM_ID, dateFormat.format(calendar.time))
            }
            DINNER_TIME_PICKER_ONCE_TAG -> {
                binding?.tvDinnerTime?.text = dateFormat.format(calendar.time)
                saveAlarmTime(DINNER_ALARM_ID, dateFormat.format(calendar.time))
            }
        }

        // Re-enable switchEatReminder after setting the alarm
        binding?.switchEatReminder?.isChecked = true
    }

    private fun setAlarms() {
        val onceTimeBreakfast = binding?.tvBreakfastTime?.text.toString()
        setAlarm(BREAKFAST_ALARM_ID, onceTimeBreakfast, "Breakfast Time", "Time to breakfast!")

        val onceTimeLunch = binding?.tvLunchTime?.text.toString()
        setAlarm(LUNCH_ALARM_ID, onceTimeLunch, "Lunch Time", "Time to lunch!")

        val onceTimeDinner = binding?.tvDinnerTime?.text.toString()
        setAlarm(DINNER_ALARM_ID, onceTimeDinner, "Dinner Time", "Time to dinner!")
    }

    private fun cancelAlarms() {
        alarmReceiver.cancelAlarm(requireContext(), BREAKFAST_ALARM_ID)
        alarmReceiver.cancelAlarm(requireContext(), LUNCH_ALARM_ID)
        alarmReceiver.cancelAlarm(requireContext(), DINNER_ALARM_ID)
    }

    private fun setAlarm(alarmId: Int, time: String, title: String, message: String) {
        if (time.isNotEmpty()) {
            val calendar = Calendar.getInstance()
            val timeArray = time.split(":").toTypedArray()
            calendar.set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
            calendar.set(Calendar.MINUTE, timeArray[1].toInt())
            calendar.set(Calendar.SECOND, 0)
            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_MONTH, 1) // Set for the next day if time is in the past
            }
            alarmReceiver.setOneTimeAlarm(requireContext(), AlarmReceiver.TYPE_ONE_TIME, calendar.timeInMillis, alarmId, title, message)
            saveAlarmTime(alarmId, time)
        } else {
//            Toast.makeText(requireContext(), "Time not set", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveAlarmTime(alarmId: Int, time: String) {
        sharedPreferences.edit().putString("ALARM_TIME_$alarmId", time).apply()
    }

    private fun loadAlarmState() {
        val isAlarmEnabled = sharedPreferences.getBoolean("ALARM_STATE", false)
        binding?.switchEatReminder?.isChecked = isAlarmEnabled
        if (isAlarmEnabled) {
            val breakfastTime = sharedPreferences.getString("ALARM_TIME_$BREAKFAST_ALARM_ID", "")
            val lunchTime = sharedPreferences.getString("ALARM_TIME_$LUNCH_ALARM_ID", "")
            val dinnerTime = sharedPreferences.getString("ALARM_TIME_$DINNER_ALARM_ID", "")
            binding?.tvBreakfastTime?.text = breakfastTime
            binding?.tvLunchTime?.text = lunchTime
            binding?.tvDinnerTime?.text = dinnerTime
        }
    }

    private fun saveAlarmState(isChecked: Boolean) {
        sharedPreferences.edit().putBoolean("ALARM_STATE", isChecked).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val BREAKFAST_TIME_PICKER_ONCE_TAG = "BreakfastTimePickerOnce"
        private const val LUNCH_TIME_PICKER_ONCE_TAG = "LunchTimePickerOnce"
        private const val DINNER_TIME_PICKER_ONCE_TAG = "DinnerTimePickerOnce"
        private const val BREAKFAST_ALARM_ID = 100
        private const val LUNCH_ALARM_ID = 101
        private const val DINNER_ALARM_ID = 102
    }
}
