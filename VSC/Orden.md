PASO 1: El docente abre la aplicación e inicia sesión

Main recibe el nombre y el legajo del docente.

GestionTurnero necesita poder buscar si ese docente esta registrado y, si no existe, registrarlo.

Docente ya tiene los atributos y el constructor necesarios para crear el objeto.

FALTA : dónde se guardan los docentes para poder encontrarlos cuando vuelvan a ingresar.

PASO 2:  El docente crea una comisión

Main solicita los datos necesarios para crearla.

Comision necesita un constructor que reciba esos datos y permita asociar la comisión con su docente y su materia.

GestionTurnero necesita poder registrar la comisión creada.

PASO 3: La docente carga los alumnos en la comision

Main solicita los datos de cada alumno.

Alumno necesita poder construirse con sus datos.

Comision necesita tener una colección de alumnos y un metodo para agregar cada alumno a esa colección.

PASO 4. El docente genera grupos si los necesita

Main pregunta si desea trabajar con grupos.

Grupo necesita contener los alumnos que lo integran y permitir agregarlos.

Comision necesita poder conservar los grupos creados, si decidieron que los grupos pertenecen a la comisión.

PASO 5. Se carga la instancia evaluativa y sus estados

Main solicita los datos de la evaluación.

InstanciaEvaluativa necesita almacenar esos datos y relacionarse con la comisión o los grupos correspondientes.

Estado necesita representar los estados permitidos para esa instancia, según las reglas que hayan definido.

PASO 6. Se generan los turnos

GestionTurnero necesita una operación que genere los turnos correspondientes a la instancia evaluativa.

Turno necesita poder representar cada turno generado.

PASO 7. Se asignan alumnos o grupos

Turno necesita permitir ser asociado un alumno o un grupo, según el tipo de evaluación.

GestionTurnero coordina esas asignaciones.

PASO 8. Se genera la instancia evaluativa

InstanciaEvaluativa sería la responsable.

PASO 9. Se genera la prioridad

Turno necesita conservar la prioridad asignada.

GestionTurnero necesita aplicar las reglas para determinar esa prioridad.

PASO 10. Se determina quién o quiénes siguen

Turnero necesita poder identificar el próximo turno según el orden y las prioridades.

Main muestra al docente qué alumno o grupo sigue.

PASO 11. Se genera el turnero

Turnero necesita recibir y administrar la colección de turnos.

GestionTurnero coordina su creación.

PASO 12. El turnero cambia las prioridades automáticamente

Turnero necesita una operación para actualizar el orden cuando cambian las prioridades.

Turno necesita permitir actualizar su prioridad, si las reglas del sistema lo requieren.