package raf.console.chitalka.domain.util

import android.graphics.Bitmap
import raf.console.chitalka.presentation.core.navigation.Navigator

typealias CoverImage = Bitmap
typealias Selected = Boolean
typealias ID = Int
typealias Route = String
typealias OnNavigate = (Navigator.() -> Unit) -> Unit
