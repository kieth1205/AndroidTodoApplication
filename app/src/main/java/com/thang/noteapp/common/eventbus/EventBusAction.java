package com.thang.noteapp.common.eventbus;

public class EventBusAction {

    public class Tasks {
        public static final String REMOVE_WORDS = "REMOVER_WORD";
        public static final String FILL_WORDS = "FILL_WORDS";
        public static final String CLOSE_DIALOG = "CLOSE_DIALOG";
        public static final String DATA_TASK = "DATA_TASK";
    }

    public class Todo {
        public static final String UPDATE_TODO = "UPDATE_TODO";
    }

    public class Tag {
        public static final String SET_TAG = "SET_TAG";
    }

}
