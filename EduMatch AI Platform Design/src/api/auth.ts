import api from './client';

export const login = async (correo: string, password: string) => {
  const response = await api.post('/auth/login', { correo, password });
  localStorage.setItem('token', response.data.token);
  localStorage.setItem('rol', response.data.rol);
  localStorage.setItem('nombre', response.data.nombre);
  localStorage.setItem('usuarioId', response.data.usuarioId);
  return response.data;
};

export const register = async (nombre: string, correo: string,
                                password: string, rol: string) => {
  const response = await api.post('/auth/register', {
    nombre, correo, password, rol
  });
  localStorage.setItem('token', response.data.token);
  localStorage.setItem('rol', response.data.rol);
  localStorage.setItem('nombre', response.data.nombre);
  localStorage.setItem('usuarioId', response.data.usuarioId);
  return response.data;
};

export const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('rol');
  localStorage.removeItem('nombre');
  localStorage.removeItem('usuarioId');
};

export const isAuthenticated = () => {
  return !!localStorage.getItem('token');
};

export const getRol = () => {
  return localStorage.getItem('rol');
};