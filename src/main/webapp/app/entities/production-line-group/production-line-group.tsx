import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './production-line-group.reducer';
import { IProductionLineGroup } from 'app/shared/model/production-line-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductionLineGroupProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ProductionLineGroup = (props: IProductionLineGroupProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { productionLineGroupList, match, loading } = props;
  return (
    <div>
      <h2 id="production-line-group-heading" data-cy="ProductionLineGroupHeading">
        Production Line Groups
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Production Line Group
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productionLineGroupList && productionLineGroupList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Factory</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productionLineGroupList.map((productionLineGroup, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${productionLineGroup.id}`} color="link" size="sm">
                      {productionLineGroup.id}
                    </Button>
                  </td>
                  <td>{productionLineGroup.name}</td>
                  <td>{productionLineGroup.description}</td>
                  <td>
                    {productionLineGroup.factory ? (
                      <Link to={`factory/${productionLineGroup.factory.id}`}>{productionLineGroup.factory.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${productionLineGroup.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${productionLineGroup.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${productionLineGroup.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Production Line Groups found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ productionLineGroup }: IRootState) => ({
  productionLineGroupList: productionLineGroup.entities,
  loading: productionLineGroup.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionLineGroup);
