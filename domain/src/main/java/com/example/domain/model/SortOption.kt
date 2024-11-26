package com.example.domain.model

enum class SortOption/*(val value: String)*/ {
    POPULAR {
        override var type = TypeSort.DOWN
            get() = field
            set(value) {
                field = value
            }
    },
    RATING {
        override var type = TypeSort.DOWN
            get() = field
            set(value) {
                field = value
            }

    },
    DATE {
        override var type = TypeSort.DOWN
            get() = field
            set(value) {
                field = value
            }

    };

    abstract var type: TypeSort
}

enum class TypeSort{
    UP, DOWN
}