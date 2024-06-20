package com.example.fitfoood.data.response

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ArtikelResponse(

	@field:SerializedName("ArtikelResponse")
	val artikelResponse: List<ArtikelResponseItem>? = null,
)

@Parcelize
data class ArtikelResponseItem(

	@field:SerializedName("article_id")
	val articleId: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("articlelabel")
	val aticleLabel: String? = null
) : Parcelable
