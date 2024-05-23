import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdopterService {

  constructor(private httpClient: HttpClient) { }

  public Get(): Observable<any> {
    return this.httpClient.get<any[]>(`${environment.api.webapi}/adopterJPA`);
  }

  // public Delete(param: RemoveUpdateModel): Observable<any> {
  //   return this.httpClient.post<any[]>(`${environment.api.webapi}/Banco/Delete`, param, { headers: this.authService.headersBitacora });
  // }

  // public GetById(id: number): Observable<any> {
  //   return this.httpClient.get<any[]>(`${environment.api.webapi}/Banco/GetById?id=${id}`, { headers: this.authService.headersBitacora });
  // }

  // public Create(params: any): Observable<any> {
  //   return this.httpClient.post<any>(`${environment.api.webapi}/Banco/Post`, params, { headers: this.authService.headersBitacora });
  // }

  // public Update(params: any): Observable<any> {
  //   return this.httpClient.put<any>(`${environment.api.webapi}/Banco/Put`, params,  { headers: this.authService.headersBitacora });
  // }
}
