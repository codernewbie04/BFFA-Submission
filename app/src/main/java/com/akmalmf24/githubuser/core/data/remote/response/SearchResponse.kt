package com.akmalmf24.githubuser.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
	@SerializedName("total_count") val totalCount: Int? = null,
	@SerializedName("incomplete_results") val incompleteResults: Boolean? = null,
	@SerializedName("items") val items: List<Users?>? = null
)



