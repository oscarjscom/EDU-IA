from django.urls import path
from .views import (
    LoginView,
    UsuariosView, UsuarioDetailView,
    DocentesView, DocenteVerificarView,
    SesionesView, SesionDetailView,
    PagosView, StatsView,
)

urlpatterns = [
    path('auth/login/',           LoginView.as_view()),
    path('usuarios/',             UsuariosView.as_view()),
    path('usuarios/<int:pk>/',    UsuarioDetailView.as_view()),
    path('docentes/',             DocentesView.as_view()),
    path('docentes/<int:pk>/verificar/', DocenteVerificarView.as_view()),
    path('sesiones/',             SesionesView.as_view()),
    path('sesiones/<int:pk>/',    SesionDetailView.as_view()),
    path('pagos/',                PagosView.as_view()),
    path('stats/',                StatsView.as_view()),
]