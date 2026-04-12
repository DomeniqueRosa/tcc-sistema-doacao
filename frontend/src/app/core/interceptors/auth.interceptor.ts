import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const isBrowser = typeof window !== 'undefined' && typeof localStorage !== 'undefined';

  const token = isBrowser ? localStorage.getItem('token') : null;

  const isLogin = req.url === 'http://localhost:8080/login';
  const isCadastroPublico =
    req.url === 'http://localhost:8080/usuarios' && req.method === 'POST';

  if (!token || isLogin || isCadastroPublico) {
    return next(req);
  }

  const reqComToken = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(reqComToken);
};