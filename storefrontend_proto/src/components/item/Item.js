import React, {Component} from 'react'
import './Item.css'

export default class Item extends Component {
  constructor (props) {
    super(props)
    this.state = {
      unit: this.props.unit
    }
  }

  onAddToCartClick (e) {
    this.props.onAddToCartCallback(e)
  }

  render() {
    return (
      <div className="col-sm-4 col-lg-4 col-md-4">
        <div className="thumbnail">
          <img src={this.props.pict} className="thumbnail-image custom-image" />
          <div className="caption margin-left-sm">
            <h4 className="pull-right">{this.props.price} Yen.</h4>
            <h4><a href={'/item/' + this.props.id}>{this.props.name}</a></h4>
            <p>{this.props.description}</p>
          </div>
          <div className="ratings margin-left-sm">
            <span>{this.state.unit} left in stock</span>
            <p className="pull-right">
              <button className="btn btn-success" onClick={e => this.onAddToCartClick(e)}>Add to Cart</button>
            </p>
            <div className="clearfix" />
          </div>
        </div>
      </div>
    )
  }
}
