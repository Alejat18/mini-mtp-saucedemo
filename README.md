# Mini MTP - Suite smoke SauceDemo

Artefactos de prueba de la Actividad 2 de Calidad en Software II,
Universidad de La Salle.

**Sistema bajo prueba:** https://www.saucedemo.com
**Version:** v1.0

## Contenido

| Ruta | Que contiene |
|---|---|
| `docs/` | Mini MTP en PDF, RTM y documento de gestion de defectos |
| `src/test/java/` | Las dos pruebas automatizadas de la suite smoke |
| `pom.xml` | Configuracion del proyecto Java |
| `.github/workflows/smoke.yml` | Ejecucion automatica de la suite en GitHub Actions |

## Pruebas de la suite

| Caso | Requisito | Que verifica |
|---|---|---|
| TC-001 | REQ-01 | El ingreso con credencial valida lleva al catalogo |
| TC-006 | REQ-03 | Agregar un producto actualiza el contador del carrito |

## Credenciales

No se guardan en el repositorio. La suite las lee de las variables de entorno
`SAUCE_USER` y `SAUCE_PASSWORD`, que en GitHub se configuran como secretos del
repositorio.

## Ejecucion local

    export SAUCE_USER=usuario
    export SAUCE_PASSWORD=clave
    mvn test

Requiere Java 17, Maven y Google Chrome. El controlador del navegador lo
resuelve Selenium Manager de forma automatica.

## Ejecucion en integracion continua

La suite corre sola en cada `push` a la rama `main` y tambien puede lanzarse a
mano desde la pestana Actions. El reporte en formato JUnit XML queda publicado
como artefacto de la ejecucion.
