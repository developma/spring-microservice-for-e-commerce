import React, {Component} from 'react'
import Item from './Item'
import request from 'superagent'

export default class ItemList extends Component {
  constructor (props) {
    super(props)
    this.state = {
      list: []
    }
  }

  componentDidMount () {
    request.get('http://localhost:8200/inventory/items/')
      .end((err, res) => {
        if (err || !res.ok) {
          window.alert('error')
        } else {
          const list = []
          res.body.forEach((element) => {
            list.push(element)
          }
          )
          this.setState({
            list: list
          })
        }
      }
      )
  }

  onAddToCartCallback (e) {
    this.props.onAddToCartCallback(e)
  }

  render () {
    return (
      <div className="wrapper">
        <div className="row">
          <div className="col-md-12">
            <div className="container">
              {
                (() => {
                  return this.state.list.map(e => (
                    <Item onAddToCartCallback={e => this.onAddToCartCallback(e)}
                      id={e.id}
                      name={e.name}
                      price={e.price}
                      pict={e.pict}
                      unit={e.unit}
                    />
                  ))
                }
                )()
              }
            </div>
          </div>
        </div>
      </div>
    )
  }
}
