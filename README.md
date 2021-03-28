<h1>PASOS A SEGUIR</h1>
<ul>
<li>CREAR BASE DE DATOS PRUEBA</li>
<li>CREAR TABLAR. UTILIZAR SCRIPT.TXT</li>
<li>mvn clean install</li>
<li>probar http://localhost:8080/bolsapuntos/</li>
</ul>
<h2>ENDPOINTS</h2>
PUT Y DELETE SE PASA EL ID COMO PATH PARAM
<h3>Cliente</h3>
http://localhost:8080/bolsapuntos/api/cliente
<h3>Concepto Puntos</h3>
http://localhost:8080/bolsapuntos/api/concepto
<h3>Regla Puntos</h3>
http//:localhost:8080/bolsapuntos/api/regla
<h3>Vencimiento Puntos</h3>
http://localhost:8080/bolsapuntos/api/vigencia
<br/><br/>
<h3>Bolsa Puntos</h3>
http://localhost:8080/bolsapuntos/api/bolsa-puntos
<h4>Cargar Puntos con Cliente y Monto:</h4>
http://localhost:8080/bolsapuntos/api/bolsa-puntos/cargar<br/>
<strong>Parametros:</strong> int idCliente, int monto
<h4>Bolsas por Cliente:</h4>
http://localhost:8080/bolsapuntos/api/bolsa-puntos/cliente/{id}<br/>
{id}: ID del Cliente
<h4>Puntos por Cliente:</h4>
http://localhost:8080/bolsapuntos/api/bolsa-puntos/puntos-cliente/{id}