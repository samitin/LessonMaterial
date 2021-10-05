package recyclerview

data class Change<out T>(val oldData:T,val newData:T)

//метод, который будет возвращать самый старый и самый новый списки элементов из payload

    fun <T> createCombinedPayload(payloads:List<Change<T>>): Change<T>{
        assert(payloads.isNotEmpty())
        val firstChange=payloads.first()
        val lastChange=payloads.last()
        return Change(firstChange.oldData,lastChange.newData)
    }

