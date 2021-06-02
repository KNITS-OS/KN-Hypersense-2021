import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './product-data.reducer';
import { IProductData } from 'app/shared/model/product-data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductDataProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ProductData = (props: IProductDataProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { productDataList, match, loading } = props;
  return (
    <div>
      <h2 id="product-data-heading" data-cy="ProductDataHeading">
        Product Data
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Product Data
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productDataList && productDataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Scraped Qty</th>
                <th>Pending Qty</th>
                <th>Rejected Qty</th>
                <th>Completed Qty</th>
                <th>Production Plan</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productDataList.map((productData, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${productData.id}`} color="link" size="sm">
                      {productData.id}
                    </Button>
                  </td>
                  <td>{productData.name}</td>
                  <td>{productData.scrapedQty}</td>
                  <td>{productData.pendingQty}</td>
                  <td>{productData.rejectedQty}</td>
                  <td>{productData.completedQty}</td>
                  <td>
                    {productData.productionPlan ? (
                      <Link to={`production-plan/${productData.productionPlan.id}`}>{productData.productionPlan.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${productData.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${productData.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${productData.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Product Data found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ productData }: IRootState) => ({
  productDataList: productData.entities,
  loading: productData.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductData);
