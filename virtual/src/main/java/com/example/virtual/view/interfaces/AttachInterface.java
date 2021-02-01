package com.example.virtual.view.interfaces;

public interface AttachInterface {

    public final AttachInterface EMPTY = new AttachInterface() {
        @Override
        public int hashCode() {
            return super.hashCode();
        }
    };
}
