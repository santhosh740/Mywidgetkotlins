package com.example.mywidgetkotlins

class ModelTwo {
    var ragu_m: String? = null
    var ragu_e: String? = null
    var kuligai_m: String? = null
    var kuligai_e: String? = null
    var emakandam_m: String? = null
    var emakandam_e: String? = null

    constructor(
        ragu_m: String?,
        ragu_e: String?,
        kuligai_m: String?,
        kuligai_e: String?,
        emakandam_m: String?,
        emakandam_e: String?
    ) {
        this.ragu_m = ragu_m
        this.ragu_e = ragu_e
        this.kuligai_m = kuligai_m
        this.kuligai_e = kuligai_e
        this.emakandam_m = emakandam_m
        this.emakandam_e = emakandam_e
    }

    constructor() {}
}