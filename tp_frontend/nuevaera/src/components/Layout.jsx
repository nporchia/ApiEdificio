import { Link, Outlet, useNavigate } from "react-router-dom"
import { useAuth } from "../context/AuthProvider";
import ToggleTheme from "./ToggleTheme";
import { MoonIcon, SunIcon } from "@heroicons/react/24/outline";
import { useTheme } from "../context/ThemeProvider";
import { useEffect } from "react";


const Layout = () => {
    const { auth, signOut } = useAuth()
    const navigate = useNavigate();

    useEffect(() => {}, [auth])

    const loggedUserData = auth.isAuthenticated && (
        <div className="text-black dark:text-white bg-gray-200 dark:bg-slate-900 duration-300 p-2 w-screen h-fit gap-4 items-center flex flex-row justify-between px-4 shadow-lg">
            <Link to={'/'} className="text-xl font-mono font-extralight p-2 text-center">Nueva Era</Link>
            <div className="flex flex-row gap-6">
                <p className="p-2 font-bold font-mono ">
                    <span className="font-mono text-sm font-light lg:flex hidden"> Usuario: {auth.username}</span>
                </p>
                <p className="p-2 font-bold font-mono">
                    <span className="font-mono text-sm font-light lg:flex hidden">Rol: {auth.rol}</span>
                </p>
                <button
                    onClick={() => navigate(`usuarios/${auth.username}`)}
                    className="dark:bg-slate-600 bg-gray-300 hover:bg-gray-200 border border-gray-300 dark:border-none text-sm duration-300 ease-linear font-bold font-mono p-2 rounded-md"
                >
                    Mi perfil
                </button>
                <button className="p-2 text-sm mr-4 rounded-md bg-red-600 hover:bg-red-400 ease-linear duration-300 hover:cursor-pointer text-white font-bold font-mono" onClick={() => signOut()}>
                    Logout
                </button>

            </div>
        </div>
    );

    return (
        <div className="flex flex-col gap-4 dark:bg-slate-800 duration-300 h-screen overflow-y-scroll">
            {loggedUserData}
            <div className="xs:mb-12 sm:mb-12">
                <Outlet />
            </div>
            <div className="flex justify-center bottom-0 fixed w-screen ">
                <footer className="text-center flex justify-center items-center shadow-lg bg-gray-200 dark:bg-slate-950 p-4 w-full  font-mono text-sm mt-4 bottom-0 lg:fixed ">
                    <div className="fixed left-0 ml-4 ">
                        <div className="flex gap-2">
                            <SunIcon className="h-6 dark:text-white" />
                            <ToggleTheme />
                            <MoonIcon className="h-6 dark:text-white" />
                        </div>
                        
                    </div>
                    <div className="flex justify-center gap-2">
                        <p className="dark:text-white"> Sistemas </p>
                        <span className="text-cyan-600 font-bold"> Nueva Era</span>
                        <span className="dark:text-white"> © 2023</span>
                    </div>
                </footer>
            </div>
        </div>
    )
}
export default Layout;


