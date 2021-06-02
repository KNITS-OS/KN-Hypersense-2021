import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './production-line.reducer';
import { IProductionLine } from 'app/shared/model/production-line.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductionLineProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ProductionLine = (props: IProductionLineProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { productionLineList, match, loading } = props;
  return (
    <div>
      <h2 id="production-line-heading" data-cy="ProductionLineHeading">
        Production Lines
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Production Line
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productionLineList && productionLineList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Location Data</th>
                <th>Production Line Group</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productionLineList.map((productionLine, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${productionLine.id}`} color="link" size="sm">
                      {productionLine.id}
                    </Button>
                  </td>
                  <td>{productionLine.name}</td>
                  <td>{productionLine.description}</td>
                  <td>
                    {productionLine.locationData ? (
                      <Link to={`location-data/${productionLine.locationData.id}`}>{productionLine.locationData.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {productionLine.productionLineGroup ? (
                      <Link to={`production-line-group/${productionLine.productionLineGroup.id}`}>
                        {productionLine.productionLineGroup.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${productionLine.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${productionLine.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${productionLine.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Production Lines found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ productionLine }: IRootState) => ({
  productionLineList: productionLine.entities,
  loading: productionLine.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionLine);
