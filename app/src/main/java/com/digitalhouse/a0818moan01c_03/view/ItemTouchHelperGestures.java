package com.digitalhouse.a0818moan01c_03.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemTouchHelperGestures extends android.support.v7.widget.helper.ItemTouchHelper.Callback {

    private GestureListener escuchador;

    public ItemTouchHelperGestures(GestureListener escuchador) {
        this.escuchador = escuchador;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // Para habilitar ambas direcciones en el gesto Swipe
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        escuchador.itemBorrado(viewHolder.getAdapterPosition());
    }

    //Para habilitar el gesto
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    public interface GestureListener{
        void itemBorrado(int posicion);
    }
}
