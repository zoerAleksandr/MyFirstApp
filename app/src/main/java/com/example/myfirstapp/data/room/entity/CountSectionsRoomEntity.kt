package com.example.myfirstapp.data.room.entity

import androidx.room.Entity

@Entity
sealed class CountSectionsRoomEntity {
    object OneSection : CountSectionsRoomEntity()
    object TwoSection : CountSectionsRoomEntity()
    object ThreeSection : CountSectionsRoomEntity()
    object FourSection : CountSectionsRoomEntity()
}
