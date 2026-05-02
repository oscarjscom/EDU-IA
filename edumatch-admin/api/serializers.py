from rest_framework import serializers
from .models import Usuario, Estudiante, Docente, Sesion, Pago

class UsuarioSerializer(serializers.ModelSerializer):
    class Meta:
        model = Usuario
        fields = ['id', 'nombre', 'correo', 'rol', 'fecha_registro', 'activo']

class EstudianteSerializer(serializers.ModelSerializer):
    usuario = UsuarioSerializer()
    class Meta:
        model = Estudiante
        fields = '__all__'

class DocenteSerializer(serializers.ModelSerializer):
    usuario = UsuarioSerializer()
    class Meta:
        model = Docente
        fields = '__all__'

class SesionSerializer(serializers.ModelSerializer):
    estudiante = EstudianteSerializer()
    docente = DocenteSerializer()
    class Meta:
        model = Sesion
        fields = '__all__'

class PagoSerializer(serializers.ModelSerializer):
    estudiante = EstudianteSerializer()
    class Meta:
        model = Pago
        fields = '__all__'