package com.app.ecitizen.data.network.dto
import com.google.gson.annotations.SerializedName


data class ServiceDto(
    @SerializedName("building_plan_approve")
    val buildingPlanApprove: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("dob_registration")
    val dobRegistration: String? = null,
    @SerializedName("fire_noc")
    val fireNoc: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("lease_deposite")
    val leaseDeposite: String? = null,
    @SerializedName("leasedeed")
    val leasedeed: String? = null,
    @SerializedName("leasedeed_re_issue")
    val leasedeedReIssue: String? = null,
    @SerializedName("marriage_reg")
    val marriageReg: String? = null,
    @SerializedName("name_transfer")
    val nameTransfer: String? = null,
    @SerializedName("property_id")
    val propertyId: String? = null,
    @SerializedName("property_tax")
    val propertyTax: String? = null,
    @SerializedName("renew_trade_licence")
    val renewTradeLicence: String? = null,
    @SerializedName("service_90_A")
    val service90A: String? = null,
    @SerializedName("sewer_connection")
    val sewerConnection: String? = null,
    @SerializedName("signature_licence")
    val signatureLicence: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("sub_division")
    val subDivision: String? = null,
    @SerializedName("trade_licence")
    val tradeLicence: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)