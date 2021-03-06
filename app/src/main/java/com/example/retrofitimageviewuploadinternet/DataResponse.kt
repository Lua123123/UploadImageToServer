package com.example.retrofitimageviewuploadinternet

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataRespone {
    @SerializedName("data")
    @Expose
    var data: Data? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null
}

class Data {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("title")
    @Expose
    var title: Any? = null

    @SerializedName("description")
    @Expose
    var description: Any? = null

    @SerializedName("datetime")
    @Expose
    var datetime: Int? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("animated")
    @Expose
    var animated: Boolean? = null

    @SerializedName("width")
    @Expose
    var width: Int? = null

    @SerializedName("height")
    @Expose
    var height: Int? = null

    @SerializedName("size")
    @Expose
    var size: Int? = null

    @SerializedName("views")
    @Expose
    var views: Int? = null

    @SerializedName("bandwidth")
    @Expose
    var bandwidth: Int? = null

    @SerializedName("vote")
    @Expose
    var vote: Any? = null

    @SerializedName("favorite")
    @Expose
    var favorite: Boolean? = null

    @SerializedName("nsfw")
    @Expose
    var nsfw: Any? = null

    @SerializedName("section")
    @Expose
    var section: Any? = null

    @SerializedName("account_url")
    @Expose
    var accountUrl: Any? = null

    @SerializedName("account_id")
    @Expose
    var accountId: Int? = null

    @SerializedName("is_ad")
    @Expose
    var isAd: Boolean? = null

    @SerializedName("in_most_viral")
    @Expose
    var inMostViral: Boolean? = null

    @SerializedName("has_sound")
    @Expose
    var hasSound: Boolean? = null

    @SerializedName("tags")
    @Expose
    var tags: List<Any>? = null

    @SerializedName("ad_type")
    @Expose
    var adType: Int? = null

    @SerializedName("ad_url")
    @Expose
    var adUrl: String? = null

    @SerializedName("edited")
    @Expose
    var edited: String? = null

    @SerializedName("in_gallery")
    @Expose
    var inGallery: Boolean? = null

    @SerializedName("deletehash")
    @Expose
    var deletehash: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("link")
    @Expose
    var link: String? = null
}