import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, Observable, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$: Observable<boolean> = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient) {
    this.checkAuthStatus();
  }

  private checkAuthStatus(): void {
    this.http.get<boolean>('http://localhost:8080/user/auth', { withCredentials: true }).subscribe({
      next: (isAuthenticated) => this.isAuthenticatedSubject.next(isAuthenticated),
      error: () => this.isAuthenticatedSubject.next(false)
    });
  }

  register(request: {username: string; email: string; password: string}): Observable<any> {
    return this.http.post('http://localhost:8080/user', request).pipe(
      tap(() => this.isAuthenticatedSubject.next(true)),
      catchError((error) => {
        console.error(error);
        return error;
      })
    )
  }

  login(credentials: {username: string, password: string}): Observable<any> {
    return this.http.post('http://localhost:8080/user/session', credentials, ).pipe(
      tap(() => this.isAuthenticatedSubject.next(true)),
      catchError((error) => {
        console.error(error);
        return error;
      })
    );
  }

  logout(): void {
    this.http.delete('http://localhost:8080/user/session', { withCredentials: true }).subscribe({
      next: () => {
        this.isAuthenticatedSubject.next(false);
        this.checkAuthStatus();
      },
      error: (err) => {
        console.error('Logout failed', err);
        this.isAuthenticatedSubject.next(false);
      }
    });
  }

  isAuthenticated(): boolean {
    return this.isAuthenticatedSubject.value;
  }
}
