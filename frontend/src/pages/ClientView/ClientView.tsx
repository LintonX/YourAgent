import React from 'react'
import NavBar from '../../components/NavBar/NavBar'
import { CLIENT_VIEW_NAVBAR_ITEMS } from '../../constants'

function ClientView() {
  return (
    <>
      <NavBar navBarItems={CLIENT_VIEW_NAVBAR_ITEMS}/>
    </>
  )
}

export default ClientView