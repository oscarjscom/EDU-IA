from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.permissions import AllowAny
from rest_framework import status
from rest_framework_simplejwt.tokens import RefreshToken
from django.contrib.auth.hashers import check_password
from .models import Usuario, Estudiante, Docente, Sesion, Pago
from .serializers import (
    UsuarioSerializer, EstudianteSerializer,
    DocenteSerializer, SesionSerializer, PagoSerializer
)

# Auth
class LoginView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        correo = request.data.get('correo')
        password = request.data.get('password')

        print(f"[LOGIN] correo: {correo}")
        print(f"[LOGIN] password: {password}")

        try:
            usuario = Usuario.objects.get(correo=correo)
            print(f"[LOGIN] usuario encontrado ID={usuario.id} rol={usuario.rol}")
        except Usuario.DoesNotExist:
            print("[LOGIN] usuario NO encontrado")
            return Response({'error': 'Credenciales incorrectas'}, status=401)
        except Exception as ex:
            print(f"[LOGIN] error: {ex}")
            return Response({'error': str(ex)}, status=500)

        pwd_ok = check_password(password, usuario.password)
        print(f"[LOGIN] check_password: {pwd_ok}")

        if not pwd_ok:
            return Response({'error': 'Credenciales incorrectas'}, status=401)

        if usuario.rol != 'ADMIN':
            print(f"[LOGIN] rol incorrecto: {usuario.rol}")
            return Response({'error': 'Acceso solo para administradores'}, status=403)

        refresh = RefreshToken()
        refresh['usuario_id'] = usuario.id
        refresh['rol']        = usuario.rol
        refresh['nombre']     = usuario.nombre

        return Response({
            'token':     str(refresh.access_token),
            'rol':       usuario.rol,
            'nombre':    usuario.nombre,
            'usuarioId': usuario.id,
        })

# Usuarios
class UsuariosView(APIView):
    def get(self, request):
        usuarios = Usuario.objects.all().order_by('-fecha_registro')
        return Response(UsuarioSerializer(usuarios, many=True).data)

class UsuarioDetailView(APIView):
    def put(self, request, pk):
        try:
            usuario = Usuario.objects.get(pk=pk)
        except Usuario.DoesNotExist:
            return Response({'error': 'No encontrado'}, status=404)
        usuario.activo = request.data.get('activo', usuario.activo)
        usuario.rol    = request.data.get('rol',    usuario.rol)
        usuario.save()
        return Response(UsuarioSerializer(usuario).data)

    def delete(self, request, pk):
        try:
            usuario = Usuario.objects.get(pk=pk)
        except Usuario.DoesNotExist:
            return Response({'error': 'No encontrado'}, status=404)
        usuario.delete()
        return Response(status=204)

# Docentes
class DocentesView(APIView):
    def get(self, request):
        docentes = Docente.objects.select_related('usuario').all()
        return Response(DocenteSerializer(docentes, many=True).data)

class DocenteVerificarView(APIView):
    def put(self, request, pk):
        try:
            docente = Docente.objects.get(pk=pk)
        except Docente.DoesNotExist:
            return Response({'error': 'No encontrado'}, status=404)
        docente.verificado = request.data.get('verificado', docente.verificado)
        docente.save()
        return Response(DocenteSerializer(docente).data)

# Sesiones
class SesionesView(APIView):
    def get(self, request):
        sesiones = Sesion.objects.select_related(
            'estudiante__usuario', 'docente__usuario'
        ).all().order_by('-fecha')
        return Response(SesionSerializer(sesiones, many=True).data)

class SesionDetailView(APIView):
    def put(self, request, pk):
        try:
            sesion = Sesion.objects.get(pk=pk)
        except Sesion.DoesNotExist:
            return Response({'error': 'No encontrado'}, status=404)
        sesion.estado = request.data.get('estado', sesion.estado)
        sesion.save()
        return Response(SesionSerializer(sesion).data)

# Pagos
class PagosView(APIView):
    def get(self, request):
        pagos = Pago.objects.select_related('estudiante__usuario').all().order_by('-fecha_pago')
        return Response(PagoSerializer(pagos, many=True).data)

# Stats para el dashboard admin
class StatsView(APIView):
    def get(self, request):
        return Response({
            'totalUsuarios':   Usuario.objects.count(),
            'totalEstudiantes': Usuario.objects.filter(rol='ESTUDIANTE').count(),
            'totalDocentes':   Usuario.objects.filter(rol='DOCENTE').count(),
            'totalSesiones':   Sesion.objects.count(),
            'sesionesHoy':     Sesion.objects.filter(estado='PROGRAMADA').count(),
            'totalPagos':      Pago.objects.count(),
            'docentesVerificados': Docente.objects.filter(verificado=True).count(),
        })