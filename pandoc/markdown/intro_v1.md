---
author: Jose Juan Montiel
title: 'On call tutti'
date: Feb 1, 2024
revealjs-url: 'reveal.js'
theme: isovera
css:
  - 'https://fonts.googleapis.com/css?family=Roboto+Slab:700'
---

# Reuse, repair & upcycle

Últimamente he tenido algo de tiempo para meditar sobre como se construye (siempre según mi opinion) el mundo tecnológico donde vivimos, y con el que trabajamos. Y parece que se construye bloque sobre bloque, usando elementos sencillos que construyen otros mayores. Y en el caso concreto de la programación de alto nivel, la manera de ordenar a las maquinas que hagan lo que queremos, tenemos varios lenguajes donde elegir el que mas nos cuadre, ¿según en la manera que pensamos? y sobre estos lenguajes construimos librerías para solucionar problemas genéricos que el lenguaje no cubre, y sobre estas librerías, creamos framework que facilitan la agrupación de herramientas y metodologías para abordar otros problemas mas grandes, que a veces se crean en esas agrupaciones inferiores, separando la solución en distintas capas, y con estos construimos productos que aportan valor a la sociedad, aunque a veces le creen problemas nuevos.

Siempre me acordare de un compañero que decía (según le había dicho otro): "operaciones con cadenas, en eso se basan todos los programas" y yo añado transformaciones de datos las llamamos ahora, y tienen que ser eficientes y distribuidas, ¿pero porque andamos todo el dia transformando datos?, bueno esa es una pregunta, para otro dia.

Pero vamos al tema, ¿habéis realizado últimamente alguna prueba técnica para algún trabajo?, ¿habéis tenido que implementar algún algoritmo, desde cero para resolver un problema?, ¿quizás habéis practicado vuestras habilidades de resolución de problemas en alguna plataforma de programación?, ¿habéis resuelto algún puzzle?, ¿quizá un cubo de Rubik?, ¿os habéis preguntado por el algoritmo de resolución, y las matemáticas que hay detrás?, eso para otro dia, también.

Me voy a permitir hacer "sobre-ingeniería" (o no, siempre me llamo la atención este concepto), y me voy a permitir usarlo para contar esta historia. ¿A caso no es también la manera en la que construimos las soluciones, a veces?, ¿puede considerase un monolito una sobre-ingeniería?, ¿a caso los micro-servicios?, ¿como reunamos los componentes, para aportar valor?

Sostenibilidad: analizar, mejorar y reusar. 

Supongamos que queremos rellenar el hueco (según mi opinion) entre los lenguajes de programación y las librerías, el hueco de los algoritmos específicos. Y pongamos que para empezar, queremos aportar valor en los aplicativos de gestión de recursos en un call center de asistencia técnica, y para ello queremos resolver el algoritmo: dado un conjunto de rangos de fechas, tener la mejor manera de representar esos rangos aplanados, según una serie de atributos, y asi poder saber dado un momento del tiempo a que persona del call center tenemos que derivar la llamada según su disponibilidad horaria y nivel.

Vamos a montar un monorepo, porque queremos compartir el código entre las distintas piezas, y vamos a usar Bazel porque queremos poder usar distintos lenguajes, y todo eso (y mas) lo vamos a meter en un repositorio de GitHub para que podamos colaborar, y automatizar el proceso de construcción de las distintas soluciones, y vamos a usar github actions para validar que las distintas soluciones cumplen con los resultados esperados, para unas entradas dadas, y vamos ... a ir poniendo en issues todo lo que se nos ocurra.

¿Te animas a probar? https://josejuanmontiel.github.io/on-call-tutti/ pero cuiado "Que no hay dos cosas igual, tampoco tan diferentes, y no te creas todo lo que piensas, pero piensa todo siempre"
