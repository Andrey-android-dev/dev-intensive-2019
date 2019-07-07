package ru.skillbranch.devintensive

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.core.Is
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.Chat
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun test_instance() {
        val user2 = User("2", "John", "Cena")
    }

    @Test
    fun test_factory() {
//        val user = User.makeUser("John Cena")
//        val user1 = User.makeUser("John Wick")
        val user = User.makeUser("John Wick")
        val user2 = user.copy(id = "2", lastName = "Cena", lastVisit = Date())
        print("$user \n $user2")
    }


    @Test
    fun test_decomposition() {
        val user = User.makeUser("John Wick")

        fun getUserInfo() = user;

        val (id, firstName, lastName) = getUserInfo()

        println("$id, $firstName, $lastName")
    }


    @Test
    fun test_copy() {
        val user = User.makeUser("John Wick")
        val user2 = user.copy(id = "2")

        if (user == user2) {
            println("equals ${user.hashCode()} $user \n ${user2.hashCode()} $user2")
        } else {
            println("not equals ${user.hashCode()} $user \n ${user2.hashCode()} $user2")
        }

        if (user === user2) {
            println("equals ${user.hashCode()} $user \n ${user2.hashCode()} $user2")
        } else {
            println("not equals ${user.hashCode()} $user \n ${user2.hashCode()} $user2")
        }
    }

    @Test
    fun test_copy2() {
        val user = User.makeUser("John Wick")
        val user2 = user.copy(lastVisit = Date().add(-2, TimeUnits.SECOND))
        val user3 = user.copy(lastName = "Cena", lastVisit = Date().add(2, TimeUnits.HOUR))

        println(
            """
            ${user.lastVisit?.format()}
            ${user2.lastVisit?.format()}
            ${user3.lastVisit?.format()}
        """.trimIndent()
        )

    }


    @Test
    fun test_data_maping() {
        val user = User.makeUser("Дозоров Андрей")
        println(user)

        val userView = user.toUserView()

        userView.printMe()
    }


    @Test
    fun test_abstract_factory() {
        val user = User.makeUser("Дозоров Андрей")
        val txtMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any text message", type = "text")
        val imgMessage = BaseMessage.makeMessage(user, Chat("0"), payload = "any image url", type = "image")

//        when(imgMessage){
//            is BaseMessage -> println("this is a base message")
//            is TextMessage -> println("this is a text message")
//            is ImageMessage ->println("this is an image message")
//        }
        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())

    }


    @Test
    fun test_parseFullName() {
        val fullName = null
        val result: Pair<String?, String?> = Utils.parseFullName(fullName)
        assertThat(result.first, Is(nullValue()))
        assertThat(result.second, Is(nullValue()))
        println(result)

        val fullName1 = ""
        val result1: Pair<String?, String?> = Utils.parseFullName(fullName1)
        assertThat(result1.first, Is(nullValue()))
        assertThat(result1.second, Is(nullValue()))
        println(result1)

        val fullName2 = " "
        val result2: Pair<String?, String?> = Utils.parseFullName(fullName2)
        assertThat(result2.first, Is(nullValue()))
        assertThat(result2.second, Is(nullValue()))
        println(result2)

        val fullName3 = "John"
        val result3: Pair<String?, String?> = Utils.parseFullName(fullName3)
        assertThat(result3.first, Is(equalTo("John")))
        assertThat(result3.second, Is(nullValue()))
        println(result3)
    }


    @Test
    fun test_toInitials() {
        val result1 = Utils.toInitials("john", "doe")
        assertThat(result1, Is(equalTo("JD")))

        val result2 = Utils.toInitials("john", null)
        assertThat(result2, Is(equalTo("J")))

        val result3 = Utils.toInitials(null, null);
        assertThat(result3, Is(nullValue()))

        val result4 = Utils.toInitials(" ", "");
        assertThat(result4, Is(nullValue()))
    }


    @Test
    fun test_humanizeDiff() {
        assertThat(Date().add(-2, TimeUnits.HOUR).humanizeDiff(), Is(equalTo("2 часа назад")))
        assertThat(Date().add(-5, TimeUnits.DAY).humanizeDiff(), Is(equalTo("5 дней назад")))
        assertThat(Date().add(2, TimeUnits.MINUTE).humanizeDiff(), Is(equalTo("через 2 минуты")))
        assertThat(Date().add(7, TimeUnits.DAY).humanizeDiff(), Is(equalTo("через 7 дней")))
        assertThat(Date().add(-400, TimeUnits.DAY).humanizeDiff(), Is(equalTo("более года назад")))
        assertThat(Date().add(400, TimeUnits.DAY).humanizeDiff(), Is(equalTo("более чем через год")))
    }

    @Test
    fun test_plural() {
        assertThat(TimeUnits.SECOND.plural(1), Is(equalTo("1 секунду")))
        assertThat(TimeUnits.MINUTE.plural(4), Is(equalTo("4 минуты")))
        assertThat(TimeUnits.HOUR.plural(19), Is(equalTo("19 часов")))
        assertThat(TimeUnits.DAY.plural(222), Is(equalTo("222 дня")))
    }

    @Test
    fun test_transliteration() {
        val result1 = Utils.transliteration("Женя Стереотипов")
        assertThat(result1, Is(equalTo("Zhenya Stereotipov")))

        val result2 = Utils.transliteration("Amazing Петр", "_")
        assertThat(result2, Is(equalTo("Amazing_Petr")))
    }

    @Test
    fun test_builder() {
        var user = User.Builder()
            .id("1")
            .firstName("Vasya")
            .lastName("Pupkin")
            .avatar("avatar")
            .rating(10)
            .respect(5)
            .lastVisit(Date().add(-2, TimeUnits.DAY))
            .isOnline(true)
            .build()
        println(user)
    }

    @Test
    fun test_time() {
        val date1 = Date()
        val date2 = Date().add(-1, TimeUnits.HOUR)
        println(date1.time - date2.time)
    }


}
