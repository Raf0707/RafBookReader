package com.byteflipper.everbook.domain.util

import android.graphics.Bitmap
import com.byteflipper.everbook.presentation.core.navigation.Navigator

typealias CoverImage = Bitmap
typealias Selected = Boolean
typealias ID = Int
typealias Route = String
typealias OnNavigate = (Navigator.() -> Unit) -> Unit
