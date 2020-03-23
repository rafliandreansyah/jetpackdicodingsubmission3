package com.azhara.jetpackdicodingsubmission3.utils

import androidx.paging.PagedList
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

object PagedListUtil {

    fun <T> mockPageList(list: List<T>): PagedList<T> {
        val pageList = mock(PagedList::class.java) as PagedList<T>

        `when`(pageList[ArgumentMatchers.anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        `when`(pageList.size).thenReturn(list.size)

        return pageList
    }
}