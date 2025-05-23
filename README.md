# 📝 ToDoOfflineAppMVVM — Kotlin + MVVM + Room + Retrofit

Aplicación Android de lista de tareas (To-Do) desarrollada con **Kotlin** y arquitectura **MVVM**. Implementa persistencia local con **Room**, consumo de API con **Retrofit**, y soporte completo para **modo offline**.  
Este proyecto fue desarrollado como parte de un **reto técnico** para una vacante como Android Developer.

---

## ✅ Cumplimiento de Requisitos

| Requisito                                   | Implementado |
|--------------------------------------------|--------------|
| Arquitectura MVVM                          | ✅           |
| Retrofit + OkHttp + Moshi                  | ✅           |
| Persistencia local con Room                | ✅           |
| Caché y modo sin conexión                  | ✅           |
| Actualización y eliminación local          | ✅           |
| Inyección de dependencias con Hilt         | ✅           |
| UI con XML + Material Design 3             | ✅           |
| ViewBinding                                 | ✅           |
| Pruebas unitarias e instrumentadas         | ✅           |

---

## 📂 Estructura del Proyecto (Capas)

```
├── data
│   ├── local → Room (DAO, entidades, DB)
│   ├── remote → Retrofit (API, modelos)
│   └── mapper → Mapeos entre modelos
├── domain → Repositorio y modelo de dominio
├── ui
│   ├── todo → Fragmento principal
│   ├── adapter → RecyclerView
│   └── viewmodel → ViewModel de ToDo
├── di → Módulos de Hilt (RepositoryModule, NetworkModule)
└── utils → Helpers generales (conexión, enums)
```

---

## 🔧 Tecnologías Utilizadas

- 🟣 **Kotlin 2.0.21**
- 🧠 **Arquitectura MVVM**
- 🔌 **Retrofit + OkHttp**
- 🔄 **Room (persistencia local)**
- 🧩 **Hilt (inyección de dependencias)**
- 📡 **Moshi (parser JSON)**
- 🎯 **Coroutines**
- 🎨 **Material Design 3**
- 🧩 **ViewBinding**

---

## 🚀 Funcionalidades

- 🔄 Consume el API: https://jsonplaceholder.typicode.com/todos
- 💾 Guarda los To-Do en Room como caché
- 📴 Funciona sin conexión si hay datos guardados
- ✅ Muestra tareas con título y checkbox editable
- ❌ Elimina tareas individual o múltiple (solo local)
- ☑️ Permite marcar tareas como completadas (local)
- ⚠️ Muestra Snackbars claros ante errores de red

---

## 🧪 Pruebas Realizadas

- `ToDoViewModelTest`: verifica la lógica de carga desde API o caché.
- `ToDoDaoTest`: inserta y consulta tareas en Room (memoria).

---

## 🗂 APK Firmado

Se generó el APK en **modo release**, firmado con una clave personalizada.  
📍 Ruta:  
```
/app/release/app-release.apk
```
link : https://drive.google.com/file/d/1fOrJuopmw2l4qpr7xLL3jZEc0JntFh1t/view

---

## 🖼 Capturas de Pantalla

iconoApp
![image](https://github.com/user-attachments/assets/58a1b9de-1eec-4a13-969c-4bff7fe518ea)

abrir la aplicacion con internet trae los To Do del servidor
![image](https://github.com/user-attachments/assets/76204902-e525-4722-8f3d-dbd35705b5e5)

Eliminando un registro
![image](https://github.com/user-attachments/assets/e8cf26e3-4e83-4813-a38c-7f5a474b413f)

Eliminando multiple registros
![image](https://github.com/user-attachments/assets/10a4d8fe-cdb3-497e-b0c1-8d5ae8c4fa0a)
![image](https://github.com/user-attachments/assets/f228d767-376e-4e63-81b7-8000661bb58e)

Editando registros
![image](https://github.com/user-attachments/assets/7953b460-92b3-4d03-beb5-1798e4c561a6)

Creando Registro
![image](https://github.com/user-attachments/assets/c652d1ee-688d-4144-b8bf-3fc8e7f13791)
![image](https://github.com/user-attachments/assets/074d2e85-ae43-420e-95a0-c9b43b020384)
![image](https://github.com/user-attachments/assets/a888399b-1c47-49f8-86e1-ea83f24c21b3)
![image](https://github.com/user-attachments/assets/3c8ecdf2-4b4a-45e3-95b4-6e661c97a52c)

Deteccion modo offline
![image](https://github.com/user-attachments/assets/7be0962d-a441-434e-9634-a6d0b3bb95a4)
![image](https://github.com/user-attachments/assets/24bfd007-080d-48f1-b2f2-dc317f6859d4)
Editando registros offline y volviendo abrir la app
![image](https://github.com/user-attachments/assets/ad9a11be-5c90-4bd2-b441-1ad7b643499d)

Una vez regresa el internet sigue con los mismos datos 
![image](https://github.com/user-attachments/assets/61627aed-5e92-4170-8842-1fb463db674d)




---

## ❓ Decisiones Técnicas

- Se usó `Flow` en Room para observar datos en tiempo real sin polling manual.
- Se eligió Moshi por su integración directa con Retrofit.
- Se implementó una lógica de `NetworkUtils` para controlar el flujo online/offline.
- Se separaron capas con claridad para asegurar mantenibilidad y testabilidad.

---

## 🛠 Problemas y Soluciones

| Problema | Solución |
|---------|----------|
| `assertThat` fallaba en instrumented tests | Se reemplazó por `assertTrue` para evitar conflictos con dependencias |
| IDs duplicados desde API vs Room | Se usó `localId` autogenerado y se mapeó contra el `id` del backend |
| Dispatchers.Main no inicializado en tests | Se utilizó `Dispatchers.setMain()` con `StandardTestDispatcher` |

---

## 💡 Posibles Mejoras (Proyecto Real)

- Agregar pantalla de detalle por tarea
- Permitir edición de tareas
- Implementar sincronización bidireccional real (con backend)
- Migrar a Jetpack Compose
- Añadir paginación para listas extensas

---

## 📦 Instalación

1. Clona este repositorio:
   ```bash
   git clone https://github.com/AlbertoMontes01/ToDoOfflineAppMVVM.git
   cd ToDoOfflineAppMVVM
   ```
2. Ábrelo en **Android Studio**
3. Conecta un emulador o dispositivo con **API 23+**
4. Ejecuta o instala el **APK release**

---

## 🧠 Autor

Desarrollado por Alberto Montes como parte del proceso técnico para vacante Android.  
Contacto: dav.vazquez1719@gmail.com

---
