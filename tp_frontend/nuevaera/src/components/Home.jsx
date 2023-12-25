import React, { useEffect, useState } from 'react';
import useAxiosPrivate from "../hooks/useAxios";
import EdificiosList from './edificio/EdificiosList';
import UsuariosList from './usuarios/UsuariosList';
import { useAuth } from "../context/AuthProvider";



const Home = () => {
    const { auth } = useAuth();
  
    const isAdmin = auth.rol === "ROLE_ADMINISTRADOR" && <UsuariosList />

    return (
        <div>
            <div className='flex md:flex-row xs:flex-col sm:flex-col m-12 gap-6'>
                <EdificiosList/>
                {isAdmin}
            </div>
        </div>
    );

};

export default Home;