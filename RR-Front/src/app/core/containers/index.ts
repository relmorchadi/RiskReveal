import {MainComponent} from './main/main.component';
import {NavbarComponent} from './navbar/navbar.component';
import {UnauthorizedComponent} from "./unauthorized/unauthorized.component";

export const CONTAINERS = [MainComponent, NavbarComponent, UnauthorizedComponent];

export * from './unauthorized/unauthorized.component'
export * from './navbar/navbar.component';
export * from './main/main.component';
