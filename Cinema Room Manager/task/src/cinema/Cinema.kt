package cinema

const val FIRST_PRICE = 10
const val SECOND_PRICE = 8
const val AVERAGE_SEATS = 60

var ticketCount = emptyArray<Pair<Int, Int>>()
var currentIncome = 0

fun main() {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seats = readLine()!!.toInt()
    val cinema = MutableList(rows) { MutableList(seats) {'S'} } 
     
     
    while(true) {
        when (printMenu()) {
        1 -> showTheSeats(cinema)
        2 -> requestTicket(cinema)
        3 -> statistics(cinema)
        0 -> return
        }
    }
}

fun printMenu(): Int {
    println("\n1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    return readLine()!!.toInt()
} 

fun showTheSeats(cinema: MutableList<MutableList<Char>>) {
    println("Cinema:\n ")
    for (i in 1..cinema[0].size) 
        print("$i ")
    for (i in 1..cinema.size) 
        print("\n$i ${cinema[i - 1].joinToString(" ")}")
}

fun requestTicket(cinema: MutableList<MutableList<Char>>) {
    println("Enter a row number:")
    val row = readLine()!!.toInt()
    println("Enter a seat number in that row:")
    val seat = readLine()!!.toInt()
    buyTicket(row, seat, cinema) 
}

fun calcTicketPrice(row: Int, cinema: MutableList<MutableList<Char>>): Int {
    val price = when {
    cinema.size * cinema[0].size <= AVERAGE_SEATS -> FIRST_PRICE
    cinema.size / 2 >= row -> FIRST_PRICE
    else -> SECOND_PRICE
    }
    currentIncome += price
    return price
}

fun buyTicket(row: Int, seat: Int, cinema: MutableList<MutableList<Char>>) {
    try {
    if (cinema[row-1][seat-1] == 'B') {
        println ("That ticket has already been purchased!")
        requestTicket(cinema)
    } else {
        println("Ticket price: $${calcTicketPrice(row, cinema)}")
        cinema[row-1][seat-1] = 'B'
        ticketCount += Pair(row, seat) 
        }
    }
    catch(e: IndexOutOfBoundsException) {
    println("Wrong input!")
    }
}

fun getPercentedFormat(cinema: MutableList<MutableList<Char>>): String {
    val percentage = ((100.0 / (cinema.size * cinema[0].size)) * ticketCount.size)
    return String.format("%.2f", percentage)
}

fun getTotalIncome(cinema: MutableList<MutableList<Char>>): Int {
    val totalSeats = cinema.size * cinema[0].size
    var totalIncome = when {
        totalSeats <= AVERAGE_SEATS -> totalSeats * FIRST_PRICE
        cinema.size % 2 == 0 -> totalSeats * ((FIRST_PRICE + SECOND_PRICE) / 2)
        else -> (cinema.size / 2 * cinema.size * FIRST_PRICE + ((cinema.size / 2 + 1) * SECOND_PRICE * cinema.size))      
        }
        return totalIncome
}

fun statistics(cinema: MutableList<MutableList<Char>>) {
    println("Number of purchased tickets: ${ticketCount.size}")
    println("Percentage: ${getPercentedFormat(cinema)}%")
    println("Current income: $$currentIncome")
    println("Total income: $${getTotalIncome(cinema)}")
}