# Astro Log

***Astro Log*** permite guardar, consultar y gestionar observaciones atstronómicas. Para más información consultar el documento de análisis.

---

## Funciones
- **Eventos:** Lista de eventos registrados.
- **Crear evento:** Pantalla de creación de evento.
- **Editar evento:** Pantalla de edición de evento.
- **Eliminación de evento:** Ventana emergente de eliminación de evento.
- **Visualización detallada:** Ventana emergente de consulta detallada de la información del evento.

---

## Tecnología
>Aplicación desarrollada en `Android` empleando el lenguaje `Java`.

---

### Event
La clase `Event.java` tiene:

- `Name` es un *String* que almacena el nombre del evento.
- `Description` es un *String* que contiene la descripción detallada del evento.
- `Year` es un *Int* que guarda el año seleccionado por el usuario.
- `Month` es un *Int* que retiene el mes seleccionado por el usuario.
- `Day` es un *Int* que almacena el día seleccionado por el usuario.
- `Category` es un *Enum* que asigna la categoría del evento.
- `CategoryPhoto` es un *Int* que contiene el id de la imagen correspondiente al la categoría elegida por el usuario.

#### EventAdapter
La clase `EventAdapter.java` crea la *View* asociada a la lista de eventos de la Actividad principal `MainActivity.java`.

```
    public class EventAdapter extends ArrayAdapter
{
    Context context;
    int idItemLayout;
    List<Event> events;

    public EventAdapter(@NonNull Context context, int idItemLayout, @NonNull List<Event> events) {
        super(context, idItemLayout, events);
        this.context = context;
        this.idItemLayout = idItemLayout;
        this.events = events;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Crear vista de esta fila
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(idItemLayout, parent, false);

        // Evento en esta posición
        Event event = events.get(position);

        // Poner el nombre
        TextView tvName = convertView.findViewById(R.id.tvEventName);
        tvName.setText(event.name);

        // Poner la foto
        ImageView ivCategoryPhoto = convertView.findViewById(R.id.ivEventCategoryImage);
        ivCategoryPhoto.setImageDrawable(context.getDrawable(event.categoryPhoto));

        //Poner la fecha
        TextView tvDate = convertView.findViewById(R.id.tvEventDate);
        tvDate.setText(event.simpleDate);crea la 

        return convertView;
    }
}
```

#### EventLayout
El layout `event_item.xml` establece la representación visual de cada evento.

- *Imageview* que muestra la imagen de la categoría asignada al evento.
- *TextView* que muestra el nombre del evento.
- *TextView* que muestra la fecha registrada del evento en formato simple.

#### DetailedEventLayout
El layout `detailed_event.xml` establece un *layout* personalizado para el *Dialog* que muestra información extendida del evento en la Actividad `MainActivity.java`.

- `TextView` que muestra el nombre del evento.
- `TextView` que muestra la descripción del evento.
- `TextView` que muestra la fecha registrada del evento en formato extendido.
- `ImageView` que muestra la imagen de la categoría.

### EventCategory
La clase `EventCategory.java` tiene:

- `EventCategoryImage` es un *Int* que contiene el id de la imagen correspondiente al la categoría.
- `EventCategory` es un *Enum* que marca la categoría del evento.

#### EventCategoryAdapter
La clase `EventCategoryAdapter.java` crea la *View* asociada al **Dialog** de la selección de categoría en la Actividad de crear nuevo evento `NewEvent.java`.

```
public class EventCategoryAdapter extends ArrayAdapter<EventCategory> {
    Context context;
    int idItemLayout;
    List<EventCategory> eventCategories;

    public EventCategoryAdapter(@NonNull Context context, int idItemLayout, @NonNull List<EventCategory> eventCategories) {
        super(context, idItemLayout, eventCategories);
        this.context = context;
        this.idItemLayout = idItemLayout;
        this.eventCategories = eventCategories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Crear vista de esta fila
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(idItemLayout, parent, false);

        // Categoría en esta posición
        EventCategory eventCategory = eventCategories.get(position);

        // Poner la foto
        ImageView ivCategoryPhoto = convertView.findViewById(R.id.ivEventCategoryImage);
        ivCategoryPhoto.setImageDrawable(context.getDrawable(eventCategory.eventCategoryImage));

        return convertView;
    }
}
```

#### EventCategoryLayout
El layout `event_category_item.xml` establece la representación visual de cada categoría.

- *ImageView* que muestra la imagen de la categoría.

#### EventCategoryDialog
El layout `event_category_dialog.xml` establece un layout personalizado para el *Dialog* de selección de evento.

- `GridView` que estructura y dispone las categorías.

---

## Mejoras planeadas
- [x] Eliminación de evento.
- [x] Dialog para la selección de categoría.
- [x] Dialog para la visualización detallada de la información del evento.
- [ ] Edición de evento.
- [ ] Diseño e implementación de estilo visual.

