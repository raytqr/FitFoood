package com.example.fitfoood.data.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("ArticleResponse")
	val articleResponse: List<ArticleResponseItem>
)

data class ArticleResponseItem(

	@field:SerializedName("article_id")
	val articleId: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("category")
	val category: String
)
