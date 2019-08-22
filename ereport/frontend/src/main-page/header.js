import React from 'react';
import logo from './ereport.png'

const Header = (props) => (
  <header className="row">
    <div className="col-md-5">
     <img src={logo} className="logo" alt="logo" />
    </div>
    <div className="col-md-7 mt-4 subtitle">
       {props.subtitle}
    </div>
  </header>
);

export default Header;