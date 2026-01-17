import { Routes } from '@angular/router';
import { CadastroComponent } from './cadastro/cadastro.component';
import { ConsultaComponent } from './consulta/consulta.component';
import { Dashboard } from './dashboard/dashboard';

export const routes: Routes = [
    {path: '', redirectTo: '/cadastro', pathMatch: 'full' },    
    {path: 'cadastro', component: CadastroComponent },
    {path: 'consulta', component: ConsultaComponent },
    {path: 'doacao/dashboard', component : Dashboard},
];
