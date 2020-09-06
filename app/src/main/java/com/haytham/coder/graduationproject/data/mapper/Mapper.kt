package com.haytham.coder.graduationproject.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}