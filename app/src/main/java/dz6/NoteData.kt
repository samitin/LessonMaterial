package dz6

import android.os.Parcel
import android.os.Parcelable

data class NoteData(var noteName: String?, var noteDate: NoteDate?, var noteDescription: String?):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            TODO("noteDate"),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(noteName)
        parcel.writeString(noteDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteData> {
        override fun createFromParcel(parcel: Parcel): NoteData {
            return NoteData(parcel)
        }

        override fun newArray(size: Int): Array<NoteData?> {
            return arrayOfNulls(size)
        }
    }

}
