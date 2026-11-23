# Registro de legajos

En **Gestión de alumnos**, elegí **Registrar alumno nuevo** para cargar sus datos personales una sola vez. Después usá **Inscribir alumno existente** e indicá el código de comisión y el legajo. Podés repetir la inscripción en otra comisión. Para ingresar como docente ya registrado, usá **Ingresar** con el legajo. Para un alta, completá los tres campos y elegí **Registrar docente**.

Los alumnos usan legajos enteros positivos y los docentes pueden usar legajos de hasta 30 caracteres. Para controlar duplicados, MySQL compara los legajos de texto sin distinguir mayúsculas, minúsculas ni acentos. En los legajos numéricos, `42`, `00042` y `+42` corresponden a la misma clave. Los legajos de origen se conservan para mantener las referencias existentes.

## Inicialización y base existente

El inicializador requiere MySQL 8.0.29 o posterior y permisos para crear tablas y disparadores. Al iniciar, crea la tabla `persona` e incorpora los legajos que ya existen. Los nombres y apellidos se mantienen en sus tablas originales, una vez por persona. Cada nueva inscripción solo agrega una fila en `comision_alumno`.

Para actualizar una instalación existente, hacé una copia de seguridad y cerrá las instancias de Turnero antes de iniciar esta versión. Si la carga detecta dos registros con una misma clave, detiene el inicio e indica el legajo en conflicto. Revisá el caso y corregí el legajo que corresponda junto con sus referencias antes de volver a iniciar. La actualización se puede repetir y conserva las filas de alumnos, docentes, comisiones, grupos, turnos y notas.
