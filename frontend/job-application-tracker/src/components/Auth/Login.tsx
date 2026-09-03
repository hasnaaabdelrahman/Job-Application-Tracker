import '../../login.css';

function Login() {
    return (
        <>
            <div className="container-fluid p-0">
                <div className="row min-vh-100 g-0">
                    <div className="col-md-6 p-0">
                        <img
                            src="/src/assets/images.jpeg"
                            alt="Login"
                            className="w-100 h-100 object-fit-cover"
                        />
                    </div>
                    <div className="col-md-6 d-flex align-items-center justify-content-center">

                        <form className="w-75">
                            <h1 className='form-header'>Welcome</h1>
                            <div className="mb-3">
                                <input type="email" className="form-control" id="inputEmail4" placeholder='Email' />
                            </div>
                            <div className="mb-3">
                                <input type="password" className="form-control" id="inputPassword4" placeholder='Password' />
                            </div>
                            <div className="mb-3">
                                <div className="form-check">
                                    <input className="form-check-input" type="checkbox" id="gridCheck" />
                                    <label className="form-check-label" htmlFor="gridCheck" >
                                        Remember me
                                    </label>
                                </div>
                            </div>
                            <div className="mb-3">
                                <a href="#" className="form-label">
                                    Forgot your password?
                                </a>
                            </div>

                            <div className="row  g-2">
                                <div className="col-auto">
                                    <button type="submit" className="btn btn-primary">
                                        Sign in
                                    </button>
                                </div>

                                <div className="col-auto">
                                    <button type="button" className="btn btn-primary">
                                        Sign up
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </>
    )
}
export default Login;