<h1>PASOS A SEGUIR</h1>
<ul>
<li>CREAR BASE DE DATOS PRUEBA</li>
<li>CREAR TABLAR. UTILIZAR SCRIPT.TXT</li>
<li>mvn clean install</li>
<li>probar http://localhost:8080/bolsapuntos/</li>
</ul>
<h2>ENDPOINTS</h2>
PUT Y DELETE SE PASA EL ID COMO PATH PARAM
<br/><strong>EJEMPLOS DE USOS EN peticiones.txt</strong>
<h3>Cliente</h3>
http://localhost:8080/bolsapuntos/api/cliente

**Parametros**: nombre,apellido,fecha

<h4>Clientes con puntos a vencer en x dias</h4>
http://localhost:8080/bolsapuntos/api/cliente/punto-vencido?dias=X

**Parametros**: int dias

Para POST se pasa un JSON(Cliente)

```json
{
    "nombre":string,
    "apellido":string,
    "nroDocumento":string,
    "tipoDocumento":string,
    "nacionalidad":string,
    "email":string,
    "telefono":string,
    "fechaNacimiento":string

}
```

<h3>Concepto Puntos</h3>
http://localhost:8080/bolsapuntos/api/concepto

Para POST se pasa un JSON(Concepto)

```json
{
  "descripcion":string,
  "puntosRequerido": integer

}
```

<h3>Regla Puntos</h3>
http//:localhost:8080/bolsapuntos/api/regla

Para POST se pasa un JSON(Regla)

```json
{
  "limiteInferior": string,
  "limiteSuperior": string,
  "equivalencia": integer

}
```

<h3>Vencimiento Puntos</h3>
http://localhost:8080/bolsapuntos/api/vigencia

```json
{
  "fechaInicio":string,
  "fechaFin":string,
  "duracion":integer

}
```

<br/><br/>
<h3>Bolsa Puntos</h3>
http://localhost:8080/bolsapuntos/api/bolsa-puntos
<h4>Cargar Puntos con Cliente y Monto:</h4>

**Parametros**: int idCliente, int puntosDesde, int puntosHasta

http://localhost:8080/bolsapuntos/api/bolsa-puntos/cargar
<br/>
<strong>Parametros:</strong> int idCliente, int monto
<h4>Bolsas por Cliente:</h4>
http://localhost:8080/bolsapuntos/api/bolsa-puntos/cliente/{id}
<br/>
{id}: ID del Cliente
<h4>Puntos por Cliente:</h4>
http://localhost:8080/bolsapuntos/api/bolsa-puntos/puntos-cliente/{id}
<br/>
<br/>
<h3>Uso Puntos</h3>
http://localhost:8080/bolsapuntos/api/usoPuntos/

Para POST se pasa un JSON(Utilizar puntos)
```json
{
    id_cliente: integer
    id_concepto_uso: integer
}
```
Para GET(Obtener Reporte)

**Parametros**: int id_cliente, Date fecha_uso, int id_concepto_uso



