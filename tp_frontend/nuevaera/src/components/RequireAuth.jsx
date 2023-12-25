import { Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthProvider";

const RequireAuth = ({ allowedRoles }) => {
    const { auth } = useAuth();
    
    if (allowedRoles.includes(auth.rol)) {
        return <Outlet/>;
    } else {
        return <h1>NOT ALLOWED</h1>;
    }
}

export default RequireAuth;