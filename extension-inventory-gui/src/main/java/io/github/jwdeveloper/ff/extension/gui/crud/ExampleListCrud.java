package io.github.jwdeveloper.ff.extension.gui.crud;

import io.github.jwdeveloper.ff.extension.gui.core.ExampleList;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryComponent;
import new_version.implementation.events.ButtonClickEvent;

public class ExampleListCrud<T> implements InventoryComponent {

    ExampleList<T> list;
    private  CrudListController<T> listViewModel;

    public ExampleListCrud(ExampleList<T> list) {
        this.list = list;
       // listViewModel = new CrudListController<T>(list);
    }


    @Override
    public void onCreate(InventoryDecorator decorator) {
        /*decorator.withComponent(list);
        decorator.withButton(builder ->
        {
            builder.setLocation(0, 7)
                    .setDescription(options ->
                    {
                        options.setTitle(getTranslator().get("gui.base.delete.title"));
                    })
                    .setMaterial(Material.BARRIER)
                    .setObserver(listViewModel.deleteObserver());
        });

        decorator.withButton(builder ->
        {
            builder
                    .setLocation(0, 5)
                    .setDescription(options ->
                    {
                        options.setTitle(decorator.translator().get("gui.base.edit.title"));
                    })
                    .setMaterial(Material.WRITABLE_BOOK)
                    .setObserver(listViewModel.editObserver());
        });

        decorator.withButton(builder ->
        {
            builder.setLocation(0, 6)
                    .setDescription(options ->
                    {
                        options.setTitle(decorator.translator().get("gui.base.insert.title"));
                    })
                    .setMaterial(Material.CRAFTING_TABLE)
                    .setObserver(listViewModel.insertObserver());
        });

        decorator.withButton(builder ->
        {
            builder.setLocation(getHeight() - 1, 4)
                    .setObserver(listViewModel.cancelObserver());
        });


        decorator.withEvents(eventsManager ->
        {
            eventsManager.onOpen().subscribe(openGuiEvent ->
            {
                listViewModel.setState(CrudListState.None);
            });
            eventsManager.onClose().subscribe(openGuiEvent ->
            {
                listViewModel.setState(CrudListState.None);
            });
        });
       // onContentClick(listViewModel::selectHandler);
         */
    }

  /*  public void onDelete(ButtonClickEvent event) {
        listViewModel.onDeleteEvent = event;
    }

    public void onEdit(ButtonClickEvent event) {
        listViewModel.onEditEvent = event;
    }

    public void onInsert(ButtonClickEvent event) {
        listViewModel.onInsertEvent = event;
    }

    public void onGet(ButtonClickEvent event) {
        listViewModel.onGetEvent = event;
    }*/
}
