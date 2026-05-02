from django.db import models

class Usuario(models.Model):
    ROL_CHOICES = [
        ('ESTUDIANTE', 'Estudiante'),
        ('DOCENTE', 'Docente'),
        ('ADMIN', 'Admin'),
    ]
    nombre = models.CharField(max_length=100)
    correo = models.EmailField(unique=True)
    password = models.CharField(max_length=255)
    rol = models.CharField(max_length=20, choices=ROL_CHOICES)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    activo = models.BooleanField(default=True)

    class Meta:
        db_table = 'usuarios'

class Estudiante(models.Model):
    usuario = models.OneToOneField(Usuario, on_delete=models.CASCADE)
    nivel = models.CharField(max_length=50, null=True, blank=True)
    objetivos = models.TextField(null=True, blank=True)
    horas_disponibles = models.CharField(max_length=20, null=True, blank=True)
    horario_preferido = models.CharField(max_length=100, null=True, blank=True)
    duracion_sesion = models.CharField(max_length=50, null=True, blank=True)
    foto_url = models.CharField(max_length=255, null=True, blank=True)
    ubicacion = models.CharField(max_length=100, null=True, blank=True)

    class Meta:
        db_table = 'estudiantes'

class Docente(models.Model):
    usuario = models.OneToOneField(Usuario, on_delete=models.CASCADE)
    biografia = models.TextField(null=True, blank=True)
    ubicacion = models.CharField(max_length=100, null=True, blank=True)
    tarifa_hora = models.DecimalField(max_digits=8, decimal_places=2, null=True, blank=True)
    experiencia = models.CharField(max_length=100, null=True, blank=True)
    foto_url = models.CharField(max_length=255, null=True, blank=True)
    rating_promedio = models.FloatField(default=0.0)
    total_sesiones = models.IntegerField(default=0)
    verificado = models.BooleanField(default=False)

    class Meta:
        db_table = 'docentes'

class Sesion(models.Model):
    ESTADO_CHOICES = [
        ('PROGRAMADA', 'Programada'),
        ('COMPLETADA', 'Completada'),
        ('CANCELADA', 'Cancelada'),
    ]
    estudiante = models.ForeignKey(Estudiante, on_delete=models.CASCADE)
    docente = models.ForeignKey(Docente, on_delete=models.CASCADE)
    materia = models.CharField(max_length=100)
    tema = models.CharField(max_length=100, null=True, blank=True)
    fecha = models.DateField()
    hora_inicio = models.CharField(max_length=10)
    hora_fin = models.CharField(max_length=10)
    plataforma = models.CharField(max_length=50, null=True, blank=True)
    link_reunion = models.CharField(max_length=255, null=True, blank=True)
    estado = models.CharField(max_length=20, choices=ESTADO_CHOICES, default='PROGRAMADA')
    notas = models.TextField(null=True, blank=True)

    class Meta:
        db_table = 'sesiones'

class Pago(models.Model):
    ESTADO_CHOICES = [
        ('EXITOSO', 'Exitoso'),
        ('PENDIENTE', 'Pendiente'),
        ('FALLIDO', 'Fallido'),
    ]
    estudiante = models.ForeignKey(Estudiante, on_delete=models.CASCADE)
    monto = models.DecimalField(max_digits=10, decimal_places=2)
    plan = models.CharField(max_length=50)
    estado = models.CharField(max_length=20, choices=ESTADO_CHOICES)
    fecha_pago = models.DateTimeField(auto_now_add=True)
    fecha_vencimiento = models.DateTimeField(null=True, blank=True)

    class Meta:
        db_table = 'pagos'