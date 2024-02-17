package com.example.uunnoo

object Datastore {
    fun createCards() {
        data class UnoCard(val number: String, val color: String)

        class UnoCardManager {
            val unoCardList: MutableList<UnoCard> = mutableListOf()

            init {
                // Karten für jede Farbe und Zahl hinzufügen
                val colors = listOf("Red", "Blue", "Green", "Yellow")
                val numbers = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

                // Doppelte Karten (außer 0)
                for (color in colors) {
                    for (number in numbers.subList(1, numbers.size)) {
                        unoCardList.add(UnoCard(number, color))
                        unoCardList.add(UnoCard(number, color))
                    }
                }

                // Einzelne 0-Karten
                for (color in colors) {
                    unoCardList.add(UnoCard("0", color))
                }

                // Zieh Zwei, Retour, Aussetzen Karten
                val specialCards = listOf("Draw Two", "Reverse", "Skip")

                for (color in colors) {
                    for (specialCard in specialCards) {
                        unoCardList.add(UnoCard(specialCard, color))
                        unoCardList.add(UnoCard(specialCard, color))
                    }
                }

                // Farbenwahlkarten und Zieh Vier Farbenwahlkarten
                val wildCards = listOf("Wild", "Wild Draw Four")

                for (wildCard in wildCards) {
                    unoCardList.add(UnoCard(wildCard, "Black"))
                    unoCardList.add(UnoCard(wildCard, "Black"))
                    unoCardList.add(UnoCard(wildCard, "Black"))
                    unoCardList.add(UnoCard(wildCard, "Black"))
                }

                // Sicherstellen, dass insgesamt 108 Karten erstellt wurden
                require(unoCardList.size == 108) { "Die Anzahl der Uno-Karten ist nicht korrekt (erwartet: 108, erhalten: ${unoCardList.size})" }
            }
        }
        // Beispiel für die Verwendung
        val unoCardManager = UnoCardManager()

        // Alle Karten aus der Liste abrufen und anzeigen
        for ((index, card) in unoCardManager.unoCardList.withIndex()) {
            println("Karte $index - Farbe: ${card.color} - Zahl/Zeichen: ${card.number}")
        }


    }
}