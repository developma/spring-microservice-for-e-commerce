import React, { Component } from 'react'
import Header from './components/header/Header'
import Footer from './components/footer/Footer'
import ItemList from './components/item/ItemList'
import {ToastContainer, toast} from 'react-toastify'

export default class App extends Component {

  onAddToCartCallback (e) {
    // TODO Add codes to add clicked item to cart.
    toast.success("An item has added to cart successfully.", {
      position: toast.POSITION.TOP_CENTER
    })
  }

  render () {
    return (
      <div>
        <Header />
        <ItemList onAddToCartCallback={e => this.onAddToCartCallback(e)}/>
        <Footer />
        <ToastContainer />
      </div>
    )
  }
}
