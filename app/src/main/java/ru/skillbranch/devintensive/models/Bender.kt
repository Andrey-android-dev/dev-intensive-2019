package ru.skillbranch.devintensive.models

/**
 * Type description here....
 *
 * Created by Andrey on 14.07.2019
 */
class Bender(var status : Status = Status.NORMAL, var question : Question = Question.NAME) {

    fun askQuestion() : String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL ->Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL ->Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer : String) : Pair<String, Triple<Int,Int,Int>>{
        return if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            if (question.question.equals(Question.IDLE)) {
                "Отлично - ты справился\nНа этом вопросов больше нет" to status.color
            } else {
                "Отлично - ты справился\n${question.question}" to status.color
            }
        } else {
            if (status.equals(Status.CRITICAL)) {
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                status = status.nextStatus()
                "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }

    enum class Status(val color : Triple<Int,Int,Int>) {
        NORMAL(Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        CRITICAL(Triple(255,0,0));

        fun nextStatus() : Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }

    }

    enum class Question(val question : String, val answers:List<String>) {

        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun validate(answer: String): String {
                if (answer.trim().get(0).isUpperCase()) {
                    return ""
                } else {
                    return "Имя должно начинаться с заглавной буквы"
                }
            }
            override fun nextQuestion(): Question = PROFESSION;
        },

        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun validate(answer: String): String {
                if (answer.trim().get(0).isLowerCase()) {
                    return ""
                } else {
                    return "Профессия должна начинаться со строчной буквы"
                }
            }
            override fun nextQuestion(): Question = MATERIAL;
        },

        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun validate(answer: String): String {
                val regex = "^\\D*$".toRegex()
                if (regex.matches(answer)) {
                    return ""
                } else {
                    return "Материал не должен содержать цифр"
                }
            }
            override fun nextQuestion(): Question = BDAY;
        },


        BDAY("Когда меня создали?", listOf("2993")) {
            override fun validate(answer: String): String {
                val regex = "^\\d*$".toRegex()
                if (regex.matches(answer)) {
                    return ""
                } else {
                    return "Год моего рождения должен содержать только цифры"
                }
            }

            override fun nextQuestion(): Question = SERIAL;
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun validate(answer: String): String {
                val regex = "^\\d{7}$".toRegex()
                if (regex.matches(answer)) {
                    return ""
                } else {
                    return "Серийный номер содержит только цифры, и их 7"
                }
            }
            override fun nextQuestion(): Question = IDLE;
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun validate(answer: String): String {
                return ""
            }
            override fun nextQuestion(): Question = IDLE;
        };

        abstract fun nextQuestion() : Question

        abstract fun validate(answer : String) : String
    }

}