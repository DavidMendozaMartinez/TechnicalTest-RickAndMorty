package com.davidmendozamartinez.technicaltest.rickandmorty.ui.screen.characters

import com.davidmendozamartinez.technicaltest.rickandmorty.R

enum class Section(private val menuItemId: Int, val stringResId: Int) {
    ALL(R.id.all, R.string.nav_drawer_all),
    FAVORITES(R.id.favorites, R.string.nav_drawer_favorites);

    companion object {
        fun parse(menuItemId: Int): Section = values().first { it.menuItemId == menuItemId }
    }
}
