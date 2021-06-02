import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './business-unit.reducer';
import { IBusinessUnit } from 'app/shared/model/business-unit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBusinessUnitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BusinessUnit = (props: IBusinessUnitProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { businessUnitList, match, loading } = props;
  return (
    <div>
      <h2 id="business-unit-heading" data-cy="BusinessUnitHeading">
        Business Units
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Business Unit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {businessUnitList && businessUnitList.length > 0 ? (
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
              {businessUnitList.map((businessUnit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${businessUnit.id}`} color="link" size="sm">
                      {businessUnit.id}
                    </Button>
                  </td>
                  <td>{businessUnit.name}</td>
                  <td>{businessUnit.description}</td>
                  <td>{businessUnit.factory ? <Link to={`factory/${businessUnit.factory.id}`}>{businessUnit.factory.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${businessUnit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${businessUnit.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${businessUnit.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Business Units found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ businessUnit }: IRootState) => ({
  businessUnitList: businessUnit.entities,
  loading: businessUnit.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BusinessUnit);
