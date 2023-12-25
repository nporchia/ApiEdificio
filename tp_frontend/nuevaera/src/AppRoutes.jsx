import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from "./components/Home.jsx";
import Login from "./components/Login.jsx";
import AuthProvider from "./context/AuthProvider.js";
import PersistLogin from './components/PersistLogin.jsx';
import Layout from './components/Layout.jsx';
import Edificio from './components/edificio/Edificio.jsx';
import Unidad from './components/unidades/Unidad.jsx';
import AreaComun from './components/areasComunes/AreaComun.jsx';
import Reclamo from './components/reclamos/Reclamo.jsx';
import CrearUnidad from "./components/unidades/CrearUnidad.jsx";
import CrearEdificio from "./components/edificio/CrearEdificio.jsx";
import CrearAreaComun from "./components/areasComunes/CrearAreaComun.jsx";
import CrearReclamo from "./components/reclamos/CrearReclamo.jsx";
import Usuario from "./components/usuarios/Usuario.jsx";
import CrearUsuario from './components/usuarios/CrearUsuario.jsx';
import RequireAuth from './components/RequireAuth.jsx';
import UpdateUnidad from "./components/unidades/UpdateUnidad.jsx";
import UpdateEdificio from './components/edificio/UpdateEdificio.jsx';
import UpdateReclamo from './components/reclamos/UpdateReclamo.jsx';
import UpdateAreaComun from './components/areasComunes/UpdateAreaComun.jsx';

const ROLES = {
    ADMIN: "ROLE_ADMINISTRADOR",
    PROP: "ROLE_PROPIETARIO",
    INQ: "ROLE_INQUILINO"
}

const AppRoutes = () => {
    return (
        <AuthProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<Layout />}>
                        <Route path="login" element={<Login />} />
                        <Route element={<PersistLogin />}>
                            <Route path="*" element={<h1 className='text-white m-12'>Not Found</h1>} />
                            <Route path="/" index element={<Home />} />
                            <Route path="edificios/:id" element={<Edificio />} />

                            <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN]} />}>
                                <Route path="edificios/:id/unidades/create" element={<CrearUnidad />} />
                                <Route path="edificios/:id/areaComun/create" element={<CrearAreaComun />} />
                                <Route path="edificios/create" element={<CrearEdificio />} />
                                <Route path="edificios/:id/update" element={<UpdateEdificio />} />
                                <Route path="usuarios/create" element={<CrearUsuario />} />
                                <Route path="usuarios/:id/update" element={<Usuario />} />
                                <Route path="unidades/:id/update" element={<UpdateUnidad />} />
                                <Route path="reclamos/:id/update" element={<UpdateReclamo />} />
                                <Route path="areasComunes/:id/update" element={<UpdateAreaComun />} />
                            </Route>

                            <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN, ROLES.PROP, ROLES.INQ]} />}>
                                <Route path=":tipoReclamo/:id/reclamos/create" element={<CrearReclamo />} />
                            </Route>

                            <Route path="unidades/:id" element={<Unidad />} />
                            <Route path="areasComunes/:id" element={<AreaComun />} />
                            <Route path="reclamos/:id" element={<Reclamo />} />
                            <Route path="usuarios/:username" element={<Usuario />} />
                            
                        </Route>
                    </Route>
                </Routes>
            </Router>
        </AuthProvider>
    )
}

export default AppRoutes