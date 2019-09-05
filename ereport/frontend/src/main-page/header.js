import React from 'react';
import logo from './ereport.png'

const Header = (props) => (
  <header class="row">
    <div class="col-md-5">
     <img src={logo} class="logo" alt="logo" />
    </div>
    <div class="col-md-7 mt-5 subtitle">
       {props.subtitle}
    </div>
  </header>
);

export default Header;
